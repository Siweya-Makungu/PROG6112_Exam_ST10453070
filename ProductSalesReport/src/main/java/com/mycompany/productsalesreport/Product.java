package com.mycompany.productsalesreport;


public class Product implements IProducts {

    private String[] productNames;
    private int[][] sales;

   
    public Product(String[] productNames, int[][] sales) {
        this.productNames = productNames;
        this.sales = sales;
    }

   
    @Override
    public int GetTotalProductsSold() {

        int total = 0;

        for (int i = 0; i < sales.length; i++) {
            for (int j = 0; j < sales[i].length; j++) {
                total += sales[i][j];
            }
        }

        return total;
    }

   
    @Override
    public int GetAverageProductSold() {

        int total = GetTotalProductsSold();
        int count = 0;

        for (int i = 0; i < sales.length; i++) {
            count += sales[i].length;
        }

        return total / count;
    }

    
    @Override
    public String GetMostPopularProduct() {

        int highestSales = 0;
        int popularIndex = 0;

        for (int i = 0; i < sales.length; i++) {

            int productTotal = 0;

            for (int j = 0; j < sales[i].length; j++) {
                productTotal += sales[i][j];
            }

            if (productTotal > highestSales) {
                highestSales = productTotal;
                popularIndex = i;
            }
        }

        return productNames[popularIndex]
                + "(" + highestSales + ")";
    }
}
