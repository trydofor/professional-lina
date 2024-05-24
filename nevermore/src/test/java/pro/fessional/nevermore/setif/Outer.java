package pro.fessional.nevermore.setif;

import java.util.List;

/**
 * @author trydofor
 * @since 2024-05-22
 */
public interface Outer {

    @SetterIf
    class Pojo {
        protected long id;
        protected boolean active;
        protected Enum<?> status;
        protected List<String> items;
    }
}
