import java.util.Comparator;

public class AudioCompare implements Comparator<AudioBook>{
	
	public int compare(AudioBook bk1, AudioBook bk2) {
		if (bk1.getListeningLength() > 5) return -1;
		if (bk1.getListeningLength() < 5) return 1;
		else return 0;
	}

}
