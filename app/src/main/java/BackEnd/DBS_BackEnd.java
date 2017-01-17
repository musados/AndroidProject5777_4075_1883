package BackEnd;

/**
 * Created by musad on 17/01/2017.
 */

public class DBS_BackEnd implements IBackEnd {

    static DBS_BackEnd Instance = new DBS_BackEnd();


    /**
     * Singleton provider - get method
     *
     * @return "Instace" of the class  - DBS_BackEnd class
     * @author Moshe Nahari & Haim Milikovski
     */
    public static DBS_BackEnd getInstance(){ return Instance;}

    /**
     * Default CTOR (private)
     * Because the class have a Singleton instance
     */
    private DBS_BackEnd()
    {}
}
