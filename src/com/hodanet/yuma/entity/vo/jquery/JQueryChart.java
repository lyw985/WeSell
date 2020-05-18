package com.hodanet.yuma.entity.vo.jquery;

import java.util.Random;

public abstract class JQueryChart {
	protected String type;
	protected ConfigData data = new ConfigData();
	protected ConfigOptions options = new ConfigOptions();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ConfigData getData() {
		return data;
	}

	public void setData(ConfigData data) {
		this.data = data;
	}

	public ConfigOptions getOptions() {
		return options;
	}

	public void setOptions(ConfigOptions options) {
		this.options = options;
	}

	protected enum Colour {
		RED("rgb(255, 99, 132)"), ORANGE("rgb(255, 159, 64)"), YELLOW("rgb(255, 205, 86)"), GREEN("rgb(75, 192, 192)"),
		BLUE("rgb(54, 162, 235)"), PURPLE("rgb(153, 102, 255)"), GREY("rgb(201, 203, 207)")
		, CYAN("rgb(0, 255, 255)"), GOLD("rgb(255, 215 , 0)"), PINK("rgb(255, 192 , 203)"),
		LIGHTGREEN("rgb(144, 238 , 144)");
		private String rgb;

		private Colour(String rgb) {
			this.rgb = rgb;
		}

		public String toString() {
			return rgb;
		}

		public static Colour randomColor() {
			Colour[] colours = Colour.values();
			Random ran = new Random();
			return colours[ran.nextInt(colours.length)];
		}
	}

	protected class ConfigData {
		protected String[] labels;
		protected ConfigDataset[] datasets;

		public String[] getLabels() {
			return labels;
		}

		public void setLabels(String[] labels) {
			this.labels = labels;
		}

		public ConfigDataset[] getDatasets() {
			return datasets;
		}

		public void setDatasets(ConfigDataset[] datasets) {
			this.datasets = datasets;
		}

	}

	protected class ConfigDataset {
		protected String label;
		protected String backgroundColor;
		protected String borderColor;
		protected boolean fill = false;
		protected Object[] data;

		public ConfigDataset() {
			Colour colour = Colour.randomColor();
			backgroundColor = colour.toString();
			borderColor = colour.toString();
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getBackgroundColor() {
			return backgroundColor;
		}

		public void setBackgroundColor(String backgroundColor) {
			this.backgroundColor = backgroundColor;
		}

		public String getBorderColor() {
			return borderColor;
		}

		public void setBorderColor(String borderColor) {
			this.borderColor = borderColor;
		}

		public boolean isFill() {
			return fill;
		}

		public void setFill(boolean fill) {
			this.fill = fill;
		}

		public Object[] getData() {
			return data;
		}

		public void setData(Object[] data) {
			this.data = data;
		}

	}

	protected class ConfigOptions {
		protected boolean responsive = true;
		protected String[] data;
		protected ConfigOptionTitle title = new ConfigOptionTitle();
		protected ConfigOptionTooltips tooltips = new ConfigOptionTooltips();
		protected ConfigOptionHover hover = new ConfigOptionHover();
		protected ConfigOptionScales scales = new ConfigOptionScales();

		public boolean isResponsive() {
			return responsive;
		}

		public void setResponsive(boolean responsive) {
			this.responsive = responsive;
		}

		public String[] getData() {
			return data;
		}

		public void setData(String[] data) {
			this.data = data;
		}

		public ConfigOptionTitle getTitle() {
			return title;
		}

		public void setTitle(ConfigOptionTitle title) {
			this.title = title;
		}

		public ConfigOptionTooltips getTooltips() {
			return tooltips;
		}

		public void setTooltips(ConfigOptionTooltips tooltips) {
			this.tooltips = tooltips;
		}

		public ConfigOptionHover getHover() {
			return hover;
		}

		public void setHover(ConfigOptionHover hover) {
			this.hover = hover;
		}

		public ConfigOptionScales getScales() {
			return scales;
		}

		public void setScales(ConfigOptionScales scales) {
			this.scales = scales;
		}

	}

	protected class ConfigOptionTitle {
		protected boolean display = true;
		protected String text;

		public boolean isDisplay() {
			return display;
		}

		public void setDisplay(boolean display) {
			this.display = display;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}

	protected class ConfigOptionTooltips {
		protected boolean intersect = false;
		protected String mode = "index";

		public boolean isIntersect() {
			return intersect;
		}

		public void setIntersect(boolean intersect) {
			this.intersect = intersect;
		}

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

	}

	protected class ConfigOptionHover {
		protected boolean intersect;
		protected String mode;

		public boolean isIntersect() {
			return intersect;
		}

		public void setIntersect(boolean intersect) {
			this.intersect = intersect;
		}

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

	}

	protected class ConfigOptionScales {
		protected boolean intersect = false;
		protected String mode = "nearest";
		protected ConfigOptionScalesXAxes[] xAxes;
		protected ConfigOptionScalesYAxes[] yAxes;

		public boolean isIntersect() {
			return intersect;
		}

		public void setIntersect(boolean intersect) {
			this.intersect = intersect;
		}

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

		public ConfigOptionScalesXAxes[] getxAxes() {
			return xAxes;
		}

		public void setxAxes(ConfigOptionScalesXAxes[] xAxes) {
			this.xAxes = xAxes;
		}

		public ConfigOptionScalesYAxes[] getyAxes() {
			return yAxes;
		}

		public void setyAxes(ConfigOptionScalesYAxes[] yAxes) {
			this.yAxes = yAxes;
		}

	}

	protected class ConfigOptionScalesXAxes {
		protected boolean display = true;
		protected ConfigOptionScalesLabel scaleLabel = new ConfigOptionScalesLabel();

		public boolean isDisplay() {
			return display;
		}

		public void setDisplay(boolean display) {
			this.display = display;
		}

		public ConfigOptionScalesLabel getScaleLabel() {
			return scaleLabel;
		}

		public void setScaleLabel(ConfigOptionScalesLabel scaleLabel) {
			this.scaleLabel = scaleLabel;
		}

	}

	protected class ConfigOptionScalesYAxes {
		protected boolean display = true;
		protected ConfigOptionScalesLabel scaleLabel = new ConfigOptionScalesLabel();

		public boolean isDisplay() {
			return display;
		}

		public void setDisplay(boolean display) {
			this.display = display;
		}

		public ConfigOptionScalesLabel getScaleLabel() {
			return scaleLabel;
		}

		public void setScaleLabel(ConfigOptionScalesLabel scaleLabel) {
			this.scaleLabel = scaleLabel;
		}

	}

	protected class ConfigOptionScalesLabel {
		protected boolean display = true;
		protected String labelString;

		public boolean isDisplay() {
			return display;
		}

		public void setDisplay(boolean display) {
			this.display = display;
		}

		public String getLabelString() {
			return labelString;
		}

		public void setLabelString(String labelString) {
			this.labelString = labelString;
		}

	}

	public abstract void testData();

	public abstract void setDataLabels(String[] labels);

	public abstract void setOptionsTitleText(String text);

	public abstract void addDataset(String label, Object[] data);
}
