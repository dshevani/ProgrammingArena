package cityguides;

public class CityGuideMainImage {
	
	String imageURL;
	String imageOwnerName;
	String imageOwnerURL;
	Integer imageWidth;
	Integer imageHeight;
	
	public CityGuideMainImage(String imageURL, String imageOwnerName,
			String imageOwnerURL, Integer imageWidth, Integer imageHeight) {
		super();
		this.imageURL = imageURL;
		this.imageOwnerName = imageOwnerName;
		this.imageOwnerURL = imageOwnerURL;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getImageURL() {
		return imageURL;
	}

	public String getImageOwnerName() {
		return imageOwnerName;
	}

	public String getImageOwnerURL() {
		return imageOwnerURL;
	}

	public Integer getImageWidth() {
		return imageWidth;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageOwnerName(String imageOwnerName) {
		this.imageOwnerName = imageOwnerName;
	}

	public void setImageOwnerURL(String imageOwnerURL) {
		this.imageOwnerURL = imageOwnerURL;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}
}
