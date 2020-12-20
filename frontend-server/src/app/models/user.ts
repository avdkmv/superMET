import { UserTypes } from "../constants/user-types.enum"

export class User {
    private _username: string
    private _password: string
    private _usertype: number

    public constructor(username: string, password: string, usertype: UserTypes) {
        this._username = username
        this._password = password
        this._usertype = usertype
    }

    get username(): string {
        return this._username
    }

    set username(username: string) {
        this._username = username
    }

    get usertype(): number {
        return this._usertype
    }

    set usertype(usertype: number) {
        this._usertype = usertype
    }

    get password(): string {
        return this._password
    }

    set password(password: string) {
        this._password = password
    }
}
