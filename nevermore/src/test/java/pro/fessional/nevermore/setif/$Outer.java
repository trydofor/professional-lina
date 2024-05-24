package pro.fessional.nevermore.setif;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author trydofor
 * @since 2024-05-22
 */
public interface $Outer {

    class Pojo extends Outer.Pojo {

        public void setIdIf(Long id, boolean bool) {
            if (bool) {
                this.id = id;
            }
        }

        public void setIdIf(Supplier<Long> id, boolean bool) {
            if (bool) {
                this.id = id.get();
            }
        }

        public void setIdIf(Long id, Predicate<Long> bool) {
            if (bool.test(id)) {
                this.id = id;
            }
        }

        public void setIdIf(Long id, Predicate<Long> bool, Supplier<Long>... ids) {
            if (bool.test(id)) {
                this.id = id;
                return;
            }
            for (Supplier<Long> supplier : ids) {
                id = supplier.get();
                if (bool.test(id)) {
                    this.id = id;
                    return;
                }
            }
        }

        public void setIdIfNot(Long id, Predicate<Long> bool) {
            if (!bool.test(id)) {
                this.id = id;
            }
        }

        public void setIdIfNot(Long id, Predicate<Long> bool, Supplier<Long>... ids) {
            if (!bool.test(id)) {
                this.id = id;
                return;
            }
            for (Supplier<Long> supplier : ids) {
                id = supplier.get();
                if (!bool.test(id)) {
                    this.id = id;
                    return;
                }
            }
        }

        public void setIdIf(UnaryOperator<Long> id) {
            this.id = id.apply(this.id);
        }

    }

    public static void main(String[] args) {
        Pojo pojo = new Pojo();
    }
}
