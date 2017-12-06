package ir.pint.soltoon.soltoongame.shared.data.map;

import ir.pint.soltoon.utils.shared.facades.json.Secure;

@CorrespondingAttributes(GameObjectType.MUSKETEER)
@Secure
public class Giant extends GameObject {
    public Giant(Long id) {super(id);}
}
