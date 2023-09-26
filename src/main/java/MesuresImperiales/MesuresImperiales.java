package MesuresImperiales ;

public class MesuresImperiales {
    private int partieEntiere ;
    private int numerateur ;
    private int denominateur ;
    private Boolean invariant() {
        return denominateur != 0 ;
    }
    public MesuresImperiales(int n, int p, int q){
        partieEntiere = n ;
        numerateur = p ;
        denominateur = q ;
        reduire() ;
        assert invariant() ;
    }

    public MesuresImperiales(int p, int q) {
        partieEntiere = 0 ;
        numerateur = p ;
        denominateur = q ;
        reduire() ;
        assert invariant() ;
    }

    public MesuresImperiales(int n) {
        partieEntiere = n ;
        numerateur = 0 ;
        denominateur = 1 ;
        assert invariant() ;
    }

    public MesuresImperiales(double f, int precision) {
        partieEntiere = (int) f ;
        double partieFractionnaire = f - partieEntiere ;
        denominateur = precision ;
        numerateur = (int) Math.round(partieFractionnaire * precision) ;
        reduire() ;
        assert invariant() ;
    }

    private void reduire() {
        reduireEntier();
        reduireFraction();
    }

    public Boolean egal(MesuresImperiales y) {
        if (numerateur == 0) return partieEntiere == y.partieEntiere && y.numerateur == 0 ;
        return partieEntiere == y.partieEntiere && numerateur == y.numerateur && denominateur == y.denominateur ;
    }

    public Boolean inegal(MesuresImperiales y) {
        return !this.egal(y) ;
    }

    public Boolean plusGrandQue(MesuresImperiales y) {
        if (partieEntiere > y.partieEntiere) return true ;
        else if (partieEntiere < y.partieEntiere) return false ;
        else return numerateur * y.denominateur > y.numerateur * denominateur ;
    }

    public Boolean plusGrandOuEgalQue(MesuresImperiales y) {
        return this.egal(y) || this.plusGrandQue(y) ;
    }

    public Boolean plusPetitQue(MesuresImperiales y) {
        return !this.plusGrandOuEgalQue(y) ;
    }

    public Boolean plusPetitOuEgalQue(MesuresImperiales y) {
        return !this.plusGrandQue(y) ;
    }

    public MesuresImperiales add(MesuresImperiales y) {
        int totalPartieEntiere = partieEntiere + y.partieEntiere ;
        int totalNumerateur = numerateur * y.denominateur + y.numerateur * denominateur ;
        int totalDenominateur = denominateur * y.denominateur ;

        return new MesuresImperiales(totalPartieEntiere, totalNumerateur, totalDenominateur) ;
    }

    public MesuresImperiales multiplierParUnReel(double y, int precision) {
        double multPartieEntiere = partieEntiere * y ;
        double multNumerateur = numerateur * y ;
        double total = multPartieEntiere + multNumerateur / denominateur ;

        return new MesuresImperiales(total, precision) ;
    }

    public MesuresImperiales sub(MesuresImperiales y) {
        int difPartieEntiere = partieEntiere - y.partieEntiere ;
        int difNumerateur = numerateur * y.denominateur - y.numerateur * denominateur ;
        int difDenominateur = denominateur * y.denominateur ;

        return new MesuresImperiales(difPartieEntiere, difNumerateur, difDenominateur) ;
    }
    private void reduireEntier(){
        int total = partieEntiere * denominateur + numerateur ;
        int partieEntiereReduite = total / denominateur ;
        int numerateurReduit = total % denominateur ;

        partieEntiere = partieEntiereReduite ;
        numerateur = numerateurReduit ;
    }

    private void reduireFraction(){
        int pgdc = euclide(denominateur, numerateur) ;
        if (pgdc == 1) return ;
        denominateur = denominateur / pgdc ;
        numerateur = numerateur / pgdc ;
    }

    static private int euclide(int x, int y) {
        int rem = x ;
        while (rem > 0) {
            rem = y % x ;
            y = x ;
            x = rem ;

        }
        return y ;
    }

    public double to_double() {
        return (partieEntiere * denominateur + numerateur) / (double) denominateur ;
    }

    public String to_string() {
        if (numerateur == 0) return String.valueOf(partieEntiere) ;
        if (partieEntiere == 0) return numerateur + "/" + denominateur ;
        return partieEntiere + " " + numerateur + "/" + denominateur ;
    }
}
