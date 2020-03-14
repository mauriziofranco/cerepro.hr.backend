package centauri.academy.cerepro.rest.response;

/**
 * 
 * @author eris
 *
 */
public abstract class AbstractSurveyResponse {
	
	protected long id;
	protected String ansa;
	protected String ansb;
	protected String ansc;
	protected String ansd;
	protected String anse;
	protected String ansf;
	protected String ansg;
	protected String ansh;
	protected long position;
	
	
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}



	/**
	 * @return the ansa
	 */
	public String getAnsa() {
		return ansa;
	}



	/**
	 * @param ansa the ansa to set
	 */
	public void setAnsa(String ansa) {
		this.ansa = ansa;
	}



	/**
	 * @return the ansb
	 */
	public String getAnsb() {
		return ansb;
	}



	/**
	 * @param ansb the ansb to set
	 */
	public void setAnsb(String ansb) {
		this.ansb = ansb;
	}



	/**
	 * @return the ansc
	 */
	public String getAnsc() {
		return ansc;
	}



	/**
	 * @param ansc the ansc to set
	 */
	public void setAnsc(String ansc) {
		this.ansc = ansc;
	}



	/**
	 * @return the ansd
	 */
	public String getAnsd() {
		return ansd;
	}



	/**
	 * @param ansd the ansd to set
	 */
	public void setAnsd(String ansd) {
		this.ansd = ansd;
	}



	/**
	 * @return the anse
	 */
	public String getAnse() {
		return anse;
	}



	/**
	 * @param anse the anse to set
	 */
	public void setAnse(String anse) {
		this.anse = anse;
	}



	/**
	 * @return the ansf
	 */
	public String getAnsf() {
		return ansf;
	}



	/**
	 * @param ansf the ansf to set
	 */
	public void setAnsf(String ansf) {
		this.ansf = ansf;
	}



	/**
	 * @return the ansg
	 */
	public String getAnsg() {
		return ansg;
	}



	/**
	 * @param ansg the ansg to set
	 */
	public void setAnsg(String ansg) {
		this.ansg = ansg;
	}



	/**
	 * @return the ansh
	 */
	public String getAnsh() {
		return ansh;
	}



	/**
	 * @param ansh the ansh to set
	 */
	public void setAnsh(String ansh) {
		this.ansh = ansh;
	}



	/**
	 * @return the position
	 */
	public long getPosition() {
		return position;
	}



	/**
	 * @param position the position to set
	 */
	public void setPosition(long position) {
		this.position = position;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractSurveyResponse [id=" + id + ", ansa=" + ansa + ", ansb=" + ansb + ", ansc=" + ansc + ", ansd="
				+ ansd + ", anse=" + anse + ", ansf=" + ansf + ", ansg=" + ansg + ", ansh=" + ansh + ", position="
				+ position + "]";
	}	
	
}
