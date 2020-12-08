import { Injectable } from "@angular/core"
import { User } from "../models/user"
import { v4 as uuidv4 } from "uuid"
import { CookieService } from "ngx-cookie-service"

@Injectable({
    providedIn: "root",
})
export class UserService {
    private users = new Map<string, User>()

    constructor(private cookie: CookieService) {}

    createUser(username: string, usertype: string) {
        const user = new User(username, usertype)
        this.saveUser(user)
    }

    getUser(uuid: string) {
        return this.users.get(uuid)
    }

    private saveUser(user: User) {
        const uuid = uuidv4()
        this.cookie.set("AUTH", uuid)

        this.users.set(uuid, user)
    }
}
