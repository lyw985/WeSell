package com.hodanet.yuma.entity.vo.jquery;

import java.util.Random;

import com.alibaba.fastjson.JSONObject;

public class JQueryLineChart extends JQueryChart {
	public JQueryLineChart() {
		type = "line";
	}

	@Override
	public void testData() {
		Random random = new Random();
		data.labels = new String[] { "January", "February", "March", "April", "May", "June", "July" };
		data.datasets = new ConfigDataset[] { new ConfigDataset(), new ConfigDataset() };
		data.datasets[0].label = "My First dataset";
		data.datasets[0].data = new Integer[] { random.nextInt(100) - 50, random.nextInt(100) - 50,
				random.nextInt(100) - 50, random.nextInt(100) - 50, random.nextInt(100) - 50, random.nextInt(100) - 50,
				random.nextInt(100) - 50 };

		data.datasets[1].label = "My Second dataset";
		data.datasets[1].data = new Integer[] { random.nextInt(100) - 50, random.nextInt(100) - 50,
				random.nextInt(100) - 50, random.nextInt(100) - 50, random.nextInt(100) - 50, random.nextInt(100) - 50,
				random.nextInt(100) - 50 };

		options.title.text = "Chart.js Line Chart";
		options.scales.xAxes = new ConfigOptionScalesXAxes[] { new ConfigOptionScalesXAxes() };
		options.scales.xAxes[0].scaleLabel.labelString = "Month";
		options.scales.yAxes = new ConfigOptionScalesYAxes[] { new ConfigOptionScalesYAxes() };
		options.scales.yAxes[0].scaleLabel.labelString = "Value";
	}

	@Override
	public void setDataLabels(String[] labels) {
		data.labels = labels;
	}

	@Override
	public void setOptionsTitleText(String text) {
		options.title.text = text;
	}

	public static void main(String[] args) {
		JQueryChart chart = new JQueryLineChart();
		chart.testData();
		System.out.println(JSONObject.toJSONString(chart));
	}

	@Override
	public void addDataset(String label, Object[] data) {
		ConfigDataset[] newConfigDatasets;
		Colour colour=Colour.randomColor(existColoursList);
		existColoursList.add(colour);
		if (this.data.datasets==null) {
			newConfigDatasets=new ConfigDataset[] {new ConfigDataset(colour)} ;
		}else {
			newConfigDatasets = new ConfigDataset[this.data.datasets.length + 1];
			newConfigDatasets[this.data.datasets.length]=new ConfigDataset(colour);
			System.arraycopy(this.data.datasets, 0, newConfigDatasets, 0, this.data.datasets.length);
		}
		
		newConfigDatasets[newConfigDatasets.length - 1].label = label;
		newConfigDatasets[newConfigDatasets.length - 1].data = data;
		this.data.datasets = newConfigDatasets;
	}

}
