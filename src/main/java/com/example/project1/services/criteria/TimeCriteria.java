package com.example.project1.services.criteria;

import com.example.project1.exception.BadRequestException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tech.jhipster.service.Criteria;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class TimeCriteria implements Serializable, Criteria {
    private String fromTime;
    private String toTime;

    public TimeCriteria(TimeCriteria other){
        this.fromTime = other.fromTime == null ? null : other.fromTime;
        this.toTime = other.toTime == null ? null : other.toTime;
    }
    @Override
    public Criteria copy() {
        return new TimeCriteria(this);
    }

    public LocalDateTime formatFromTime(){
        LocalDateTime fromTime;
        try{
            fromTime = LocalDateTime.parse(this.fromTime, DateTimeFormatter.ofPattern("ddMMyyyy HHmmss"));
        }catch (DateTimeParseException ex){
            throw new BadRequestException("invalid datetime format");
        }
        return fromTime;
    }

    public LocalDateTime formatToTime(){
        LocalDateTime toTime;
        try{
            toTime = LocalDateTime.parse(this.toTime, DateTimeFormatter.ofPattern("ddMMyyyy HHmmss"));
            LocalDateTime fromTime = formatFromTime();
            if(toTime.isBefore(fromTime)){
                throw new BadRequestException("from time must be before to time");
            }
        }catch (DateTimeParseException ex){
            throw new BadRequestException("invalid datetime format");
        }
        return toTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeCriteria that = (TimeCriteria) o;
        return Objects.equals(fromTime, that.fromTime) && Objects.equals(toTime, that.toTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromTime, toTime);
    }

    @Override
    public String toString() {
        return "TimeCriteria{" +
                "fromTime='" + fromTime + '\'' +
                ", toTime='" + toTime + '\'' +
                '}';
    }
}
