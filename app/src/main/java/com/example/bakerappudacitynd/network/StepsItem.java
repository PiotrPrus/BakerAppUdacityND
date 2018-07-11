package com.example.bakerappudacitynd.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StepsItem implements Parcelable{

	public static final String KEY_STEP_DATA = "KEY_STEP_DATA";

	@SerializedName("videoURL")
	private String videoURL;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("shortDescription")
	private String shortDescription;

	@SerializedName("thumbnailURL")
	private String thumbnailURL;

	protected StepsItem(Parcel in) {
		videoURL = in.readString();
		description = in.readString();
		id = in.readInt();
		shortDescription = in.readString();
		thumbnailURL = in.readString();
	}

	public StepsItem(String videoURL, String description, int id, String shortDescription, String thumbnailURL) {
		this.videoURL = videoURL;
		this.description = description;
		this.id = id;
		this.shortDescription = shortDescription;
		this.thumbnailURL = thumbnailURL;
	}

	public static final Creator<StepsItem> CREATOR = new Creator<StepsItem>() {
		@Override
		public StepsItem createFromParcel(Parcel in) {
			return new StepsItem(in);
		}

		@Override
		public StepsItem[] newArray(int size) {
			return new StepsItem[size];
		}
	};

	public void setVideoURL(String videoURL){
		this.videoURL = videoURL;
	}

	public String getVideoURL(){
		return videoURL;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setShortDescription(String shortDescription){
		this.shortDescription = shortDescription;
	}

	public String getShortDescription(){
		return shortDescription;
	}

	public void setThumbnailURL(String thumbnailURL){
		this.thumbnailURL = thumbnailURL;
	}

	public String getThumbnailURL(){
		return thumbnailURL;
	}

	@Override
 	public String toString(){
		return 
			"StepsItem{" + 
			"videoURL = '" + videoURL + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",shortDescription = '" + shortDescription + '\'' + 
			",thumbnailURL = '" + thumbnailURL + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(videoURL);
		parcel.writeString(description);
		parcel.writeInt(id);
		parcel.writeString(shortDescription);
		parcel.writeString(thumbnailURL);
	}
}