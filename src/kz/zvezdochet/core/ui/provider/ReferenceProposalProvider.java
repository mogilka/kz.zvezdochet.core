package kz.zvezdochet.core.ui.provider;

import java.util.ArrayList;
import java.util.List;

import kz.zvezdochet.core.bean.Reference;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;

/**
 * Класс, обеспечивающий автозаполнение текстовых полей
 * @author Nataly Didenko
 *
 */
public class ReferenceProposalProvider implements IContentProposalProvider {
	private IContentProposal[] contentProposals;
	private boolean filterProposals = false;
	private List<Reference> proposals;
	 
	public ReferenceProposalProvider(List<Reference> proposals) {
		super();
		this.proposals = proposals;
	}
	 
	public IContentProposal[] getProposals(String contents, int position) {
		if (filterProposals) {
			ArrayList<Object> list = new ArrayList<Object>();
			for (Reference p : proposals) {
				if (p.getName().length() >= contents.length()
						&& p.getName().substring(0, contents.length()).equalsIgnoreCase(contents)) {
					list.add(makeContentProposal(p));
				}
			}
			return (IContentProposal[])list.toArray(new IContentProposal[list.size()]);
		}
		if (contentProposals == null) {
			contentProposals = new IContentProposal[proposals.size()];
			for (int i = 0; i < proposals.size(); i++) {
				contentProposals[i] = makeContentProposal(proposals.get(i));
			}
		}
		return contentProposals;
	}
	 
	public void setProposals(List<Reference> items) {
		this.proposals = items;
		contentProposals = null;
	}
	 
	public void setFiltering(boolean filterProposals) {
		this.filterProposals = filterProposals;
		contentProposals = null;
	}
	 
	private IContentProposal makeContentProposal(final Reference proposal) {
		return new IContentProposal() {
			public String getContent() {
				return proposal.getName();
			}
	 
			public String getDescription() {
				return proposal.getDescription();
			}
	 
			public String getLabel() {
				return proposal.getName();
			}
	 
			public int getCursorPosition() {
				return proposal.getName().length();
			}
		};
	}
}
