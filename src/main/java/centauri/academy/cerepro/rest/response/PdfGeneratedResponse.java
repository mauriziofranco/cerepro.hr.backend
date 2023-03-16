package centauri.academy.cerepro.rest.response;

public class PdfGeneratedResponse {
	
	private boolean pdfGenerated;

	public PdfGeneratedResponse(boolean pdfGenerated) {
		super();
		this.pdfGenerated = pdfGenerated;
	}

	/**
	 * @return the pdfGenerated
	 */
	public boolean isPdfGenerated() {
		return pdfGenerated;
	}

	/**
	 * @param pdfGenerated the pdfGenerated to set
	 */
	public void setPdfGenerated(boolean pdfGenerated) {
		this.pdfGenerated = pdfGenerated;
	}
	
	@Override
	public String toString() {
		return "PdfGeneratedResponse [pdfGenerated=" + pdfGenerated + "]";
	}

}
