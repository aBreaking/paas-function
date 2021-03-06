package com.sitech.esb.hb;



/**
 * EsbsrvMvRuploadId generated by MyEclipse Persistence Tools
 */

public class EsbsrvMvRuploadId  implements java.io.Serializable {


    // Fields    

     private Long esbcltUldirId;
     private Long esbsrvUldirId;


    // Constructors

    /** default constructor */
    public EsbsrvMvRuploadId() {
    }

    
    /** full constructor */
    public EsbsrvMvRuploadId(Long esbcltUldirId, Long esbsrvUldirId) {
        this.esbcltUldirId = esbcltUldirId;
        this.esbsrvUldirId = esbsrvUldirId;
    }

   
    // Property accessors

    public Long getEsbcltUldirId() {
        return this.esbcltUldirId;
    }
    
    public void setEsbcltUldirId(Long esbcltUldirId) {
        this.esbcltUldirId = esbcltUldirId;
    }

    public Long getEsbsrvUldirId() {
        return this.esbsrvUldirId;
    }
    
    public void setEsbsrvUldirId(Long esbsrvUldirId) {
        this.esbsrvUldirId = esbsrvUldirId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof EsbsrvMvRuploadId) ) return false;
		 EsbsrvMvRuploadId castOther = ( EsbsrvMvRuploadId ) other; 
         
		 return ( (this.getEsbcltUldirId()==castOther.getEsbcltUldirId()) || ( this.getEsbcltUldirId()!=null && castOther.getEsbcltUldirId()!=null && this.getEsbcltUldirId().equals(castOther.getEsbcltUldirId()) ) )
 && ( (this.getEsbsrvUldirId()==castOther.getEsbsrvUldirId()) || ( this.getEsbsrvUldirId()!=null && castOther.getEsbsrvUldirId()!=null && this.getEsbsrvUldirId().equals(castOther.getEsbsrvUldirId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getEsbcltUldirId() == null ? 0 : this.getEsbcltUldirId().hashCode() );
         result = 37 * result + ( getEsbsrvUldirId() == null ? 0 : this.getEsbsrvUldirId().hashCode() );
         return result;
   }   





}