import { Injectable } from "@angular/core";
import { Appointment } from "../models/appointment";
import { MessagingService } from './messaging.service';

@Injectable({
    providedIn: "root",
})
export class AppointmentService {
    private appointments = new Array<Appointment>();

    constructor(private msgService: MessagingService) {}

    getAvailableAppointments() {
        return this.appointments.filter((a) => a.patient == null);
    }

    getAppointmentsForDoctor(doctor: string) {
        return this.appointments.filter((a) => a.doctor == doctor);
    }

    getAppointmentsForPatient(patient: string) {
        return this.appointments.filter((a) => a.patient == patient);
    }

    getAppointmentsForDoctorAndPatient(doctor: string, patient: string) {
        return this.getAppointmentsForDoctor(doctor).filter((a) => a.patient == patient);
    }

    createAppointmentsFor(doctor: string) {
        const count = Math.random() * 3;
        let startTime = Math.random() * 11 + 1;
        for (let i = 0; i < count; i++) {
            this.saveAppointment(doctor, Date.UTC(2020, 12, 8, startTime, 0));
            startTime++;
        }
    }

    createAppointment(doctor: string) {
        this.saveAppointment(doctor, Date.now());
    }
    
    makeAppointment(a: Appointment, patient: string) {
        let index = this.appointments.indexOf(a);
        if (index != null) {
            this.appointments[index].patient = patient;
            this.msgService.makeAppointment(a);
        }
    }

    private saveAppointment(doctor: string, date: number) {
        let appointment = new Appointment(doctor, date);
        this.appointments.push(appointment);
        this.msgService.appointment(appointment);
    }
}
