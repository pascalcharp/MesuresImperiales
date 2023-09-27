package MesuresImperiales ;

/**
 * La classe MesuresImperiales sert à manipuler des nombres données sous forme d'un entier accompagné d'une fraction en
 * puissances de 2.  Le nombre peut donc être spécifié par: un entier, une fraction, un entier suivi de la partie
 * fractionnaire.
 *
 * Exemples:
 *
 * 1 1/2   4 3/64   2     5/32
 *
 * Ces nombres peuvent aussi être négatifs, par-exemple:
 * -(1 1/2) sera représenté sous la forme -1 -1/2.  Donc dans le cas d'un nombre négatif, la partie entière ET le
 * numérateur seront négatifs, par contre le dénominateur sera positif.
 *
 * Dans le cas où la partie fractionnaire sera supérieure à 1, le surplus sera transféré à la partie entière et le résidu
 * sera gardé dans la partie fractionnaire.  De sorte que la partie fractionnaire sera toujours inférieure à 1.
 *
 * Lors de la construction d'un tel nombre, les trois éléments, partie entière, numérateur et dénominateur pourront
 * avoir un signe arbitraire.  Le nombre sera toujours de telle sorte que la convention de signes soit respectée, et
 * la partie fractionnaire sera toujours irréductible.
 *
 * Par-exemple:  -3 4/16 sera mis sous la forme finale: -2 -3/4.  Donc la partie fractionnaire sera irréductible, et
 * les parties entière et fractionnaire ont le même signe.
 *
 */
public class MesuresImperiales {

    private int partieEntiere ;
    private int numerateur ;
    private int denominateur ;


    /**
     * Construit un nombre de la forme: n + p/q, où p/q sera irréductible et inférieur à 1.
     * @param n Partie entière
     * @param p Numérateur de la partie fractionnaire
     * @param q Dénominateur de la partie fractionnaire
     */
    public MesuresImperiales(int n, int p, int q){
        partieEntiere = n ;
        numerateur = p ;
        denominateur = q ;
        reduire() ;
        assert invariant() ;
    }

    /**
     * Construit un nombre de la forme: p/q.  Si p > q en valeur absolue, une partie entière sera créée. Le nombre final
     * sera alors de la forme n + p'/q.
     * @param p Numérateur
     * @param q Dénominateur
     */
    public MesuresImperiales(int p, int q) {
        partieEntiere = 0 ;
        numerateur = p ;
        denominateur = q ;
        reduire() ;
        assert invariant() ;
    }

    /**
     * Construit un nombre sans partie fractionnaire.  Essentiellement un entier.
     * @param n Partie entièreé
     */
    public MesuresImperiales(int n) {
        partieEntiere = n ;
        numerateur = 0 ;
        denominateur = 1 ;
        assert invariant() ;
    }

    /**
     * Construit un nombre de la forme n p/q à partir d'un nombre à virgule flottante, à une précision donnée.
     * @param f Nombre à virgule flottante que l'on veut convertir.
     * @param precision Le plus grand dénominateur admissible.  En d'autres termes, la précision.
     */
    public MesuresImperiales(double f, int precision) {
        partieEntiere = (int) f ;
        double partieFractionnaire = f - partieEntiere ;
        denominateur = precision ;
        numerateur = (int) Math.round(partieFractionnaire * precision) ;
        reduire() ;
        assert invariant() ;
    }



    /**
     * Opérateur d'égalité.
     * @param y Opérande de droite
     * @return true si les deux opérandes ont la même valeur.
     */
    public Boolean egal(MesuresImperiales y) {
        if (numerateur == 0) return partieEntiere == y.partieEntiere && y.numerateur == 0 ;
        return partieEntiere == y.partieEntiere && numerateur == y.numerateur && denominateur == y.denominateur ;
    }

    /**
     * Opérateur d'inégalité
     * @param y Opérande de droite
     * @return true si les deux opérandes n'ont pas la même valeur.
     */
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

    /**
     * retourne la somme des deux opérandes
     * @param y Opérande de droite
     * @return La somme
     */
    public MesuresImperiales add(MesuresImperiales y) {
        int totalPartieEntiere = partieEntiere + y.partieEntiere ;
        int totalNumerateur = numerateur * y.denominateur + y.numerateur * denominateur ;
        int totalDenominateur = denominateur * y.denominateur ;

        return new MesuresImperiales(totalPartieEntiere, totalNumerateur, totalDenominateur) ;
    }

    /**
     * Permet de multiplier un nombre n p/q par un réel arbitraire, à une précision donnée.  Le résultat est encore sous
     * forme n p/q.
     * @param y Multiplicande réel.
     * @param precision Le plus grand dénominateur possible du résultat.
     * @return Un nombre n p/q résultat de l'opération.
     */
    public MesuresImperiales multiplierParUnReel(double y, int precision) {
        double multPartieEntiere = partieEntiere * y ;
        double multNumerateur = numerateur * y ;
        double total = multPartieEntiere + multNumerateur / denominateur ;

        return new MesuresImperiales(total, precision) ;
    }

    /**
     * Soustrait y de this, le résultat est sous forme n p/q
     * @param y Opérande de droite
     * @return La différence sous forme n p/q
     */
    public MesuresImperiales sub(MesuresImperiales y) {
        int difPartieEntiere = partieEntiere - y.partieEntiere ;
        int difNumerateur = numerateur * y.denominateur - y.numerateur * denominateur ;
        int difDenominateur = denominateur * y.denominateur ;

        return new MesuresImperiales(difPartieEntiere, difNumerateur, difDenominateur) ;
    }

    /**
     * Converti this en double
     * @return Un double ayant la même valeur que this
     */
    public double to_double() {
        return (partieEntiere * denominateur + numerateur) / (double) denominateur ;
    }

    /**
     * Convertit un nombre en String sous la forme "n p/q".  Si p=0, la forme sera "p".  Si p=0 la forme sera "p/q".
     * @return Un représentation textuelle de this
     */
    public String to_string() {
        if (numerateur == 0) return String.valueOf(partieEntiere) ;
        if (partieEntiere == 0) return numerateur + "/" + denominateur ;
        return partieEntiere + " " + numerateur + "/" + denominateur ;
    }

    /**
     * Convertit un objet String en nombre de la forme n p/q.  Les chaînes de caractères admissibles sont:
     * "XX XX/XX", "XXXX", "XXX/XXX".  Il ne peut y avoir qu'un seul espace entre les parties entière et fractionnaire.
     * @param arg Chaîne de caractère à convertir
     * @return Un nombre de la forme n p/q
     * @throws NumberFormatException si le format ne permet pas la conversion.
     */
    public static MesuresImperiales value_of(String arg) throws NumberFormatException {
        String noSpaceArg = arg.trim() ;
        String[] parties = noSpaceArg.split(" ") ;

        int partieEntiere = 0 ;
        int numerateur = 0 ;
        int denominateur = 1 ;


        if (parties.length == 1) {
            String[] facteurs = parties[0].split("/") ;
            if (facteurs.length == 1) {
                partieEntiere = Integer.parseInt(facteurs[0]) ;
            }
            else if (facteurs.length == 2) {
                numerateur = Integer.parseInt(facteurs[0]) ;
                denominateur = Integer.parseInt(facteurs[1]) ;
            }
            else {
                throw new NumberFormatException() ;
            }

        }
        else if (parties.length == 2) {
            partieEntiere = Integer.parseInt(parties[0]) ;

            String[] facteurs = parties[1].split("/") ;
            if (facteurs.length != 2) {
                throw new NumberFormatException() ;
            }
            numerateur = Integer.parseInt(facteurs[0]) ;
            denominateur = Integer.parseInt(facteurs[1]) ;

        }
        else {
            throw new NumberFormatException() ;
        }

        return new MesuresImperiales(partieEntiere, numerateur, denominateur) ;

    }

    private Boolean invariant() {
        return denominateur != 0 ;
    }

    private void reduire() {
        reduireEntier();
        reduireFraction();
    }

    private void reduireEntier(){
        int total = partieEntiere * denominateur + numerateur ;
        int partieEntiereReduite = total / denominateur ;
        int numerateurReduit = total % denominateur ;

        partieEntiere = partieEntiereReduite ;
        numerateur = numerateurReduit ;
    }

    private void reduireFraction(){
        int sgnDenominateur ;
        int sgnNumerateur ;

        if (denominateur < 0) {
            sgnDenominateur = -1 ;
        } else {
            sgnDenominateur = 1 ;
        }

        if (numerateur < 0) {
            sgnNumerateur = -1 ;
        } else {
            sgnNumerateur = 1 ;
        }

        int pgdc = euclide(sgnDenominateur * denominateur, sgnNumerateur * numerateur) ;

        denominateur = sgnDenominateur * denominateur / pgdc ;
        numerateur = sgnDenominateur * numerateur / pgdc ;
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


}
