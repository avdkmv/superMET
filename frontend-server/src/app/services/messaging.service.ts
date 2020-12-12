import { Injectable } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { Subject } from "rxjs";
import { Appointment } from "../models/appointment";

@Injectable({
    providedIn: "root",
})
export class MessagingService {
    private loginMessageSource = new Subject<boolean>();
    private signupMessageSource = new Subject<boolean>();
    private appointmentMessageSource = new Subject<Appointment>();
    private ongoingMessageSource = new Subject<Appointment>();

    loginMessage$ = this.loginMessageSource.asObservable();
    signupMessage$ = this.signupMessageSource.asObservable();
    appointmentMessage$ = this.appointmentMessageSource.asObservable();
    ongoingMessage$ = this.ongoingMessageSource.asObservable();

    constructor(private cookie: CookieService) {}

    login() {
        this.loginMessageSource.next(true);
    }

    logout() {
        this.loginMessageSource.next(false);
    }

    signup() {
        this.signupMessageSource.next(true);
    }

    appointment(appointment: Appointment) {
        this.appointmentMessageSource.next(appointment);
    }

    makeAppointment(appointment: Appointment) {
        this.ongoingMessageSource.next(appointment);
    }
}
