package com.example.tkunetworkapp.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Xavier on 2015/10/30.
 */
public class MyDataResult {
	private Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	// Inner Classes
	public static class Result {
		private String offset;

		@SerializedName("limit")
		private String limitation;

		private String count;

		private String sort;

		private List<ResultItem> results;

		public String getOffset() {
			return offset;
		}

		public void setOffset(String offset) {
			this.offset = offset;
		}

		public String getLimitation() {
			return limitation;
		}

		public void setLimitation(String limitation) {
			this.limitation = limitation;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public String getSort() {
			return sort;
		}

		public void setSort(String sort) {
			this.sort = sort;
		}

		public List<ResultItem> getResults() {
			return results;
		}

		public void setResults(List<ResultItem> results) {
			this.results = results;
		}
	}

	public static class ResultItem {
		private String _id;
		private String ParkName;
		private String Name;
		private String YearBuilt;
		private String OpenTime;
		private String Image;
		private String Introduction;

		public String get_id() {
			return _id;
		}

		public void set_id(String _id) {
			this._id = _id;
		}

		public String getParkName() {
			return ParkName;
		}

		public void setParkName(String parkName) {
			ParkName = parkName;
		}

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		public String getYearBuilt() {
			return YearBuilt;
		}

		public void setYearBuilt(String yearBuilt) {
			YearBuilt = yearBuilt;
		}

		public String getOpenTime() {
			return OpenTime;
		}

		public void setOpenTime(String openTime) {
			OpenTime = openTime;
		}

		public String getImage() {
			return Image;
		}

		public void setImage(String image) {
			Image = image;
		}

		public String getIntroduction() {
			return Introduction;
		}

		public void setIntroduction(String introduction) {
			Introduction = introduction;
		}
	}
}
