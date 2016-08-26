package grandfinale;
class Complex {
    public double real;
    public double imag;
    public String output = "";

    public Complex(double real, double imag){
        this.real += real;
        this.imag += imag;
    }
    public Complex(){
        real = 0;
        imag = 0;
    }
    public static Complex add(Complex num, Complex num2){
        Complex add = new Complex();
        add.real = num.real + num2.real;
        add.imag = num.imag + num2.imag;
        return add;
    }
    public static Complex subtract(Complex num, Complex num2){
        Complex sub = new Complex();
        sub.real = num.real - num2.real;
        sub.imag = num.imag - num2.imag;
        return sub;
    }
    
    public void print() {
        System.out.print(real+"+i"+imag);
    }
}

class Multiplication4 extends Complex {
    
    
    public static Complex multiply(Complex num1,Complex num2){
        
        double temp1 = num1.real*num2.real;
        double temp2 = num2.imag*num2.imag;	
        double temp3 = num1.real*num2.imag;
        double temp4 = num1.imag*num2.real;         // multiplication function for complex numbers
        return (new Complex(temp1-temp2,temp3+temp4));   
    }
}

class Multiplication3 extends Complex {
    
    public static Complex multiply(Complex num1,Complex num2){
        
        double temp1 = num1.real*num2.real;
        double temp2 = num2.imag*num2.imag;	
        double temp3 = (num1.real+num1.imag)*(num2.real+num2.imag);  // multiplication function for complex numbers
        return (new Complex(temp1-temp2,temp3-(temp1+temp2)));
    }    
}   

public class Problem8 extends Complex{
    public static void main(String[] args) {
        int n = 4;
        Complex[] P = new Complex[2*n];
        Complex[] Q = new Complex[2*n];
        Complex[] PQ = new Complex[2*n];
        Complex[] V = new Complex[2*n];
        Complex[] Vin = new Complex[2*n];
        double[] array1 = new double[n];
        double[] array2 = new double[n];
        //V = Evaluate(n);
        
        for(int i=0 ; i<n ; i++){
            array1[i] = i;
            array2[i] = i+10;
        }
        
        for(int i=0 ; i<n ; i++){
            P[i] = new Complex(array1[i],0);
            Q[i] = new Complex(array2[i],0);
        }
        
        for (int i=n ; i<2*n ; i++) {
            P[i] = new Complex();
            Q[i] = new Complex();
        }
        for (int i = 0; i < 2*n; i++) {
            V[i] = new Complex(Math.cos(2*Math.PI*i/2*n),Math.sin(2*Math.PI*i/2*n));  
            Vin[i] = new Complex(Math.cos(2*Math.PI*i/2*n),-Math.sin(2*Math.PI*i/2*n));
        }
        
        Complex[] SolP = new Complex[n];
        Complex[] SolQ = new Complex[n];
        Complex[] SolPQ = new Complex[n];
        SolP = FFT(P,V,n);
        SolQ = FFT(Q,V,n);
        
        for (int i = 0 ; i <= n-1 ; i++) {
            SolPQ[i] = Multiplication3.multiply(SolP[i],SolQ[i]);
        }
        
        Complex[] output = FFT(SolPQ,Vin,n);   
        
        for(int i=0 ; i<n ; i++){
            double x;
            if(output[i].imag < 0)
                x = Math.floor(output[i].imag);
            else
                x = Math.ceil(output[i].imag);
            PQ[i] = new Complex(output[i].real/n,x/n);
            PQ[i].print();
            System.out.print(" ");
        }
    }

    public static Complex[] FFT(Complex[] P, Complex[] V, int n) {
        if ( n==1 ) 
            return P;
    
        Complex pEven[] = new Complex[n/2];
        Complex pOdd[] = new Complex[n/2];
        Complex Vsquared[] = new Complex[n/2];
        
        for(int i = 0 ; i <= n/2-1  ; i++) {
            pEven[i] = P[2*i];
            pOdd[i] = P[2*i+1];
        }
        
        for(int i = 0 ; i <= n/2-1  ; i++)
            Vsquared[i] = Multiplication3.multiply(V[i],V[i]);
        
        Complex Sol_even[] = new Complex[n/2];
        Complex Sol_odd[] = new Complex[n/2];
        Sol_even = FFT(pEven,Vsquared,n/2);
        Sol_odd = FFT(pOdd,Vsquared,n/2);
        Complex Sol[] = new Complex[n];
        
        for(int i = 0 ; i <= n/2-1  ; i++) {
            Complex temp1 = Multiplication3.multiply(V[i],Sol_odd[i]);
            Sol[i] = add(Sol_even[i],temp1);
            Sol[i+n/2] = subtract(Sol_even[i],temp1);
        }
        return Sol;
    }
}