import { Injectable } from "@angular/core";
import { CookieService } from "ngx-cookie-service";
import { v4 as uuidv4 } from "uuid";
import { User } from "../models/user";
import { AppointmentService } from "./appointment.service";

@Injectable({
    providedIn: "root",
})
export class UserService {
    private users = new Map<string, User>();

    constructor(private cookie: CookieService, private appointmentService: AppointmentService) {}

    createUser(username: string, usertype: string) {
        const user = new User(username, usertype);

        const uid = this.findUser(user);

        if (uid != null) {
            this.cookie.set("AUTH", uid);
            return false;
        } else {
            this.saveUser(user);
            return true;
        }
    }

    getUser(uuid: string) {
        return this.users.get(uuid);
    }

    findPatients() {
        return this.findUsersOfType("Patient");
    }

    findDoctors() {
        return this.findUsersOfType("Doctor");
    }

    getLoggedUser() {
        return this.getUser(this.cookie.get("AUTH"));
    }

    private saveUser(user: User) {
        const uuid = uuidv4();
        this.cookie.set("AUTH", uuid);
        this.users.set(uuid, user);

        if (user.usertype == "Doctor") {
            this.appointmentService.createAppointmentsFor(user.username);
        }
    }

    private userExists(user: User) {
        const uid = this.findUser(user);
        return uid != null ? true : false;
    }

    private findUser(user: User) {
        for (let _user of this.users.entries()) {
            if (_user[1].username == user.username) {
                return _user[0];
            }
        }
        return null;
    }

    private findUsersOfType(type: string) {
        const patients = new Map<string, User>();
        for (let user of this.users.entries()) {
            if (user[1].usertype == type) {
                patients.set(user[0], user[1]);
            }
        }

        return patients;
    }
}
