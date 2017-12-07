package ir.pint.soltoon.utils.shared.data;


import ir.pint.soltoon.utils.shared.facades.json.Secure;

@Secure
public class Data{
    private String text = "hello";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Data)) return false;

        Data data = (Data) o;

        return text != null ? text.equals(data.text) : data.text == null;
    }

    @Override
    public String toString() {
        return "Data{" +
                "text='" + text + '\'' +
                '}';
    }
}