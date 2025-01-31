package it.polito.tdp.itunes.model;

public class Album implements Comparable<Album> {
	private Integer albumId;
	private String title;

	private Integer num; //numsogns che ho aggiunto iio per ottenere la query dei vertii
	public Album(Integer albumId, String title, Integer num) {
		super();
		this.albumId = albumId;
		this.title = title;
		this.num=num;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (albumId == null) {
			if (other.albumId != null)
				return false;
		} else if (!albumId.equals(other.albumId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return title;
	}

	@Override
	public int compareTo(Album a) {
		// TODO Auto-generated method stub
		return this.title.compareTo(a.getTitle());
	}
	
	
	
}
