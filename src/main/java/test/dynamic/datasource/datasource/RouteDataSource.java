package test.dynamic.datasource.datasource;

/**
 * @author wangbx
 * Created by wangbx on 2018/6/14.
 */
public class RouteDataSource extends AbstractRegisterAbleRoutingDataSource {

    public static final ThreadLocal<String> DATASOURCE = new ThreadLocal<>();

    private static final String DEFAULT_DATASOURCE = "datasource";

    @Override
    protected Object determineCurrentLookupKey() {

        if (DATASOURCE.get() == null) {
            return DEFAULT_DATASOURCE;
        }
        return DATASOURCE.get();
    }
}
