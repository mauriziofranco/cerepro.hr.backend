/**
 * 
 */
package centauri.academy.cerepro.rest.response;

/**
 * @author Marco Fulgosi
 * @author m.franco
 * 
 * Class that maps single question to send to frontend...
 *
 */
public class ResponseQuestion extends AbstractSurveyResponse {
	/* ATTRIBUTI */
	
	private String label;
	private String description;
	
	
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}



	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResponseQuestion [label=" + label + ", description=" + description + ", id=" + id + ", ansa=" + ansa
				+ ", ansb=" + ansb + ", ansc=" + ansc + ", ansd=" + ansd + ", anse=" + anse + ", ansf=" + ansf
				+ ", ansg=" + ansg + ", ansh=" + ansh + ", position=" + position + "]";
	}
	
	
	
}

