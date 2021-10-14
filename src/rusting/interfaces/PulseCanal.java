package rusting.interfaces;

import arc.struct.Seq;
import mindustry.world.Tile;

public interface PulseCanal extends PulseInstantTransportation{
    //used to update all connected blocks with new endpoints
    default Seq<PulseInstantTransportation> connected(){
        return Seq.with();
    }

    default Tile findCanalEnd(Tile tile, float maxDst){
        return null;
    }
}
