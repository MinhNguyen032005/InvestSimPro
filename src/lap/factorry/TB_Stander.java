package lap.factorry;

public class TB_Stander implements IThietBi{
    @Override
    public TV createTV() {
        return new TVTCL();
    }

    @Override
    public DT createDT() {
        return new DTCoDay();
    }

    @Override
    public TuLanh createTuLanh() {
        return new TuLanhAqua();
    }
}
