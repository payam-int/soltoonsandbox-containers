package ir.pint.soltoon.soltoongame.shared.data.map;


import ir.pint.soltoon.utils.shared.facades.json.Secure;

@CorrespondingAttributes(GameObjectType.TOWER)
@Secure
public class Tower extends GameObject {
    public Tower(Long id) { super(id);}
}
