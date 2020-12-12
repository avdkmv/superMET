import { Component, OnInit } from "@angular/core";
import { Appointment } from "src/app/models/appointment";
import { User } from "src/app/models/user";
import { AppointmentService } from "src/app/services/appointment.service";
import { MessagingService } from "src/app/services/messaging.service";
import { UserService } from "src/app/services/user.service";

@Component({
    selector: "app-appointments",
    templateUrl: "./appointments.component.html",
    styleUrls: ["./appointments.component.sass"],
})
export class AppointmentsComponent implements OnInit {
    user: User;
    appointments = new Array<Appointment>();
    ongoing = new Array<Appointment>();

    constructor(
        private userService: UserService,
        private appointmentService: AppointmentService,
        private msgService: MessagingService,
    ) {
        this.user = this.userService.getLoggedUser();
        this.updateAppointmentList();

        this.msgService.appointmentMessage$.subscribe((a) => {
            this.appointments.push(a);
        });

        this.msgService.ongoingMessage$.subscribe((a) => {
            this.updateAppointmentList();
        });
    }

    ngOnInit(): void {}

    ngAfterViewInit(): void {}

    createAppointment() {
        this.appointmentService.createAppointment(this.user.username);
    }

    makeAppointment(a: Appointment, event) {
        event.preventDefault();
        this.appointmentService.makeAppointment(a, this.user.username);
    }

    private updateAppointmentList() {
        if (this.user) {
            switch (this.user.usertype) {
                case "Patient":
                    this.appointments = this.appointmentService.getAvailableAppointments();
                    this.ongoing = this.appointmentService.getAppointmentsForPatient(this.user.username);
                    break;
                case "Doctor":
                    this.appointments = this.appointmentService.getAppointmentsForDoctor(this.user.username);
                    this.ongoing = this.appointments.filter(a => a.patient != null);
                    break;
            }
        }
    }
}
