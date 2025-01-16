package intermediate2.comp;

import java.util.Comparator;

public class IdComparator implements Comparator<UserV2> {

	@Override
	public int compare(UserV2 o1, UserV2 o2) {
		return o1.getId().compareTo(o2.getId());
	}
}
