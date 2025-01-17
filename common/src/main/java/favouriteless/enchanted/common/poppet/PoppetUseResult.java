package favouriteless.enchanted.common.poppet;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PoppetUseResult {

    private final Item poppet;
    private final ResultType result;

    private PoppetUseResult(Item poppet, ResultType result) {
        this.poppet = poppet;
        this.result = result;
    }

    public static PoppetUseResult of(Item poppet, boolean isBroken) {
        return isBroken ? successBreak(poppet) : success(poppet);
    }

    public static PoppetUseResult pass() {
        return new PoppetUseResult(null, ResultType.PASS);
    }

    public static PoppetUseResult fail(Item poppet) {
        return new PoppetUseResult(poppet, ResultType.FAIL);
    }

    public static PoppetUseResult success(Item poppet) {
        return new PoppetUseResult(poppet, ResultType.SUCCESS);
    }

    public static PoppetUseResult successBreak(Item poppet) {
        return new PoppetUseResult(poppet, ResultType.SUCCESS_BREAK);
    }

    public Item poppet() {
        return poppet;
    }

    public ResultType type() {
        return result;
    }

    public boolean isSuccess() {
        return result == ResultType.SUCCESS || result == ResultType.SUCCESS_BREAK;
    }

    public enum ResultType {
        SUCCESS,
        SUCCESS_BREAK,
        FAIL,
        PASS
    }
}
