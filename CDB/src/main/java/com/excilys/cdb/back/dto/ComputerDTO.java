package com.excilys.cdb.back.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;

import com.excilys.cdb.back.validator.DateConstraint;;

@DateConstraint(ldIntroduced = "ldIntroduced", ldDiscontinued = "ldDiscontinued")
public class ComputerDTO {

		private static final String REGEX_NO_WHITESPACE_BEGINING_END = "^[^\\s]+(\\s+[^\\s]+)*$";

		private long id;
		
		@NotBlank
		@Size(min = 3, max = 30)
		@Pattern(regexp = REGEX_NO_WHITESPACE_BEGINING_END)
		private String name;
		
		@PastOrPresent
		private LocalDate ldIntroduced;
		
		@PastOrPresent
		private LocalDate ldDiscontinued;
		
		@NumberFormat
		@Positive
		private Long companyId;
		
		private String companyName;
		
		public ComputerDTO() {	
		}
		
		public ComputerDTO(long id, String name, LocalDate ldIntroduced, LocalDate ldDiscontinued, Long companyId,
				String companyName) {
			super();
			this.id = id;
			this.name = name;
			this.ldIntroduced = ldIntroduced;
			this.ldDiscontinued = ldDiscontinued;
			this.companyId = companyId;
			this.companyName = companyName;
		}
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (companyId ^ (companyId >>> 32));
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
			if (companyId != other.companyId)
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
					+ ldDiscontinued + ", companyId=" + companyId + ", companyName=" + companyName + "]";
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
		public Long getCompanyId() {return companyId;}
		public void setCompanyId(Long companyId) {this.companyId = companyId;}
		public String getCompanyName() {return companyName;}
		public void setCompanyName(String companyName) {this.companyName = companyName;}

}
