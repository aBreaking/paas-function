package com.sitech.esb.hb;



/**
 * MovetoLocalDownladId generated by MyEclipse Persistence Tools
 */

public class MovetoLocalDownladId  implements java.io.Serializable {


    // Fields    

     private Long esbsrvDldirId;
     private Long esbsrvUldirId;


    // Constructors

    /** default constructor */
    public MovetoLocalDownladId() {
    }

    
    /** full constructor */
    public MovetoLocalDownladId(Long esbsrvDldirId, Long esbsrvUldirId) {
        this.esbsrvDldirId = esbsrvDldirId;
        this.esbsrvUldirId = esbsrvUldirId;
    }

   
    // Property accessors

    public Long getEsbsrvDldirId() {
        return this.esbsrvDldirId;
    }
    
    public void setEsbsrvDldirId(Long esbsrvDldirId) {
        this.esbsrvDldirId = esbsrvDldirId;
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
		 if ( !(other instanceof MovetoLocalDownladId) ) return false;
		 MovetoLocalDownladId castOther = ( MovetoLocalDownladId ) other; 
         
		 return ( (this.getEsbsrvDldirId()==castOther.getEsbsrvDldirId()) || ( this.getEsbsrvDldirId()!=null && castOther.getEsbsrvDldirId()!=null && this.getEsbsrvDldirId().equals(castOther.getEsbsrvDldirId()) ) )
 && ( (this.getEsbsrvUldirId()==castOther.getEsbsrvUldirId()) || ( this.getEsbsrvUldirId()!=null && castOther.getEsbsrvUldirId()!=null && this.getEsbsrvUldirId().equals(castOther.getEsbsrvUldirId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getEsbsrvDldirId() == null ? 0 : this.getEsbsrvDldirId().hashCode() );
         result = 37 * result + ( getEsbsrvUldirId() == null ? 0 : this.getEsbsrvUldirId().hashCode() );
         return result;
   }   





}