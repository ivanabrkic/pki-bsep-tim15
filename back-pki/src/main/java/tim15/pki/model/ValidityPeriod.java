package tim15.pki.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ValidityPeriod {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="serial_number", referencedColumnName = "serial_number", unique = true)
    private Certificate certificate;

    @Column(name="start_date", nullable=false)
    private Date startDate;

    @Column(name="end_date", nullable=false)
    private Date endDate;

    public ValidityPeriod() {
    }

    public ValidityPeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ValidityPeriod{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
