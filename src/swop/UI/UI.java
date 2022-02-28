package swop.UI;

public abstract class UI {

	public UI(String id) {
		this.id = id;
	}
	public abstract void load();
	private String id;

	public String getID() {
		return this.id;
	}
}
