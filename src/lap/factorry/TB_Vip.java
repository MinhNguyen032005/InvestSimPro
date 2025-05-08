package lap.factorry;

public class TB_Vip implements IThietBi{
    @Override
    public TV createTV() {
        return new TVSamSung();
    }

    @Override
    public DT createDT() {
        return new DTSamSung();
    }

    @Override
    public TuLanh createTuLanh() {
        return new TuLanhSamSung();
    }
}
