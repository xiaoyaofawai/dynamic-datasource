package test.dynamic.datasource.datasource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangbx
 * Created by wangbx on 2018/6/14.
 */
public abstract class AbstractRegisterAbleRoutingDataSource extends AbstractDataSource implements InitializingBean {
    ReentrantLock lock = new ReentrantLock();
    private Map<Object, Object> targetDataSources;
    private Object defaultTargetDataSource;
    private boolean lenientFallback = true;
    private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
    private Map<Object, DataSource> resolvedDataSources;
    private DataSource resolvedDefaultDataSource;

    public AbstractRegisterAbleRoutingDataSource() {
    }

    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        this.defaultTargetDataSource = defaultTargetDataSource;
    }

    public void setLenientFallback(boolean lenientFallback) {
        this.lenientFallback = lenientFallback;
    }

    public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
        this.dataSourceLookup = (DataSourceLookup)(dataSourceLookup != null ? dataSourceLookup : new JndiDataSourceLookup());
    }

    @Override
    public void afterPropertiesSet() {
        if (this.targetDataSources == null) {
            throw new IllegalArgumentException("Property 'targetDataSources' is required");
        } else {
            this.resolvedDataSources = new HashMap(this.targetDataSources.size());
            Iterator var1 = this.targetDataSources.entrySet().iterator();

            while(var1.hasNext()) {
                Map.Entry<Object, Object> entry = (Map.Entry)var1.next();
                Object lookupKey = this.resolveSpecifiedLookupKey(entry.getKey());
                DataSource dataSource = this.resolveSpecifiedDataSource(entry.getValue());
                this.resolvedDataSources.put(lookupKey, dataSource);
            }

            if (this.defaultTargetDataSource != null) {
                this.resolvedDefaultDataSource = this.resolveSpecifiedDataSource(this.defaultTargetDataSource);
            }

        }
    }

    protected Object resolveSpecifiedLookupKey(Object lookupKey) {
        return lookupKey;
    }

    protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
        if (dataSource instanceof DataSource) {
            return (DataSource)dataSource;
        } else if (dataSource instanceof String) {
            return this.dataSourceLookup.getDataSource((String)dataSource);
        } else {
            throw new IllegalArgumentException("Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return this.determineTargetDataSource().getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return iface.isInstance(this) ? (T) this : this.determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this) || this.determineTargetDataSource().isWrapperFor(iface);
    }

    protected DataSource determineTargetDataSource() {
        Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
        Object lookupKey = null;
        try {
            lock.lock();
            lookupKey = this.determineCurrentLookupKey();
        } finally {
            lock.unlock();
        }
        DataSource dataSource = (DataSource)this.resolvedDataSources.get(lookupKey);
        if (dataSource == null && (this.lenientFallback || lookupKey == null)) {
            dataSource = this.resolvedDefaultDataSource;
        }

        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        } else {
            return dataSource;
        }
    }

    public void register(String key, DataSource dataSource) {
        try {
            lock.lock();
            resolvedDataSources.put(key, dataSource);
        } finally {
            lock.unlock();
        }
    }

    protected abstract Object determineCurrentLookupKey();
}

