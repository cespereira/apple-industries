package com.apple.industries.data;


public class OrderItem {
    private PackageType packageType;

    private boolean isGift;

    public OrderItem(PackageType packageType, boolean isGift) {
        this.packageType = packageType;
        this.isGift = isGift;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public boolean isGift() {
        return isGift;
    }

    public void setGift(boolean gift) {
        isGift = gift;
    }
}
