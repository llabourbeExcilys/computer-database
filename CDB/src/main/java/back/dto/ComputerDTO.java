package back.dto;

import java.time.LocalDate;


public class ComputerDTO {
	
	//REQUIRED
		private long id;
		private String name;
		
		// OPTIONNAL
		private LocalDate ldIntroduced;
		private LocalDate ldDiscontinued;
		private Long companyID;
		private String companyName;
		
		public ComputerDTO() {
			
		}
		
		public ComputerDTO(long id, String name, LocalDate ldIntroduced, LocalDate ldDiscontinued, Long companyID,
				String companyName) {
			super();
			this.id = id;
			this.name = name;
			this.ldIntroduced = ldIntroduced;
			this.ldDiscontinued = ldDiscontinued;
			this.companyID = companyID;
			this.companyName = companyName;
		}
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (companyID ^ (companyID >>> 32));
			result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
			result = prime * result + (int) (id ^ (id >>> 32));
			result = prime * result + ((ldDiscontinued == null) ? 0 : ldDiscontinued.hashCode());
			result = prime * result + ((ldIntroduced == null) ? 0 : ldIntroduced.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
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
			ComputerDTO other = (ComputerDTO) obj;
			if (companyID != other.companyID)
				return false;
			if (companyName == null) {
				if (other.companyName != null)
					return false;
			} else if (!companyName.equals(other.companyName))
				return false;
			if (id != other.id)
				return false;
			if (ldDiscontinued == null) {
				if (other.ldDiscontinued != null)
					return false;
			} else if (!ldDiscontinued.equals(other.ldDiscontinued))
				return false;
			if (ldIntroduced == null) {
				if (other.ldIntroduced != null)
					return false;
			} else if (!ldIntroduced.equals(other.ldIntroduced))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "ComputerDTO [id=" + id + ", name=" + name + ", ldIntroduced=" + ldIntroduced + ", ldDiscontinued="
					+ ldDiscontinued + ", companyID=" + companyID + ", companyName=" + companyName + "]";
		}

		//Getter and Setter
		public long getId() {return id;}
		public void setId(long id) {this.id = id;}
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		public LocalDate getLdIntroduced() {return ldIntroduced;}
		public void setLdIntroduced(LocalDate ldIntroduced) {this.ldIntroduced = ldIntroduced;}
		public LocalDate getLdDiscontinued() {return ldDiscontinued;}
		public void setLdDiscontinued(LocalDate ldDiscontinued) {this.ldDiscontinued = ldDiscontinued;}
		public Long getCompanyID() {return companyID;}
		public void setCompanyID(Long companyID) {this.companyID = companyID;}
		public String getCompanyName() {return companyName;}
		public void setCompanyName(String companyName) {this.companyName = companyName;}

}
