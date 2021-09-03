package rusting.ctype;

import rusting.Varsr;

public class UnlockableAchievement extends UnlockableERContent{
    public UnlockableAchievement(String name) {
        super(name);
    }

    @Override
    public ERContentType getContentType() {
        return Varsr.content.getContentType("unlockableAchievement");
    }
}
