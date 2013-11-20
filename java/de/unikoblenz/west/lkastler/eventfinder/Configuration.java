package de.unikoblenz.west.lkastler.eventfinder;

public class Configuration {

	public enum VISUALIZATIONS {
		LIST(1), MAP(2), TIMESLIDER(3);
	
		private int value;
		private VISUALIZATIONS(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	};
	
	private VISUALIZATIONS visualization = VISUALIZATIONS.LIST;
	
	public Configuration() {
		
	}
	
	public void setVisualization(VISUALIZATIONS v) {
		visualization = v;
	}
	
	public VISUALIZATIONS getVisualization() {
		return visualization;
	}
	
	public int getVisualizationValue() {
		return visualization.getValue();
	}
}
