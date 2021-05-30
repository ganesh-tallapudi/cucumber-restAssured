package Utils;

public enum PetStatus {

    Available("available"),
    Pending("pending"),
    Sold("sold");

    private String status;
    PetStatus(String status){
        this.status = status;
    }


}
