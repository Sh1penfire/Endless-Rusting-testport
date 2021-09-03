package rusting.ctype;

import rusting.Varsr;

public abstract class MappableERContent extends ERContent{

    public final String name;
    public String localizedName;

    public MappableERContent(String name){
        this.name = name;
        Varsr.content.handleContent(this);
    }
}
