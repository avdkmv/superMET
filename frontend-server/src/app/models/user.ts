export class User {
    private _username: string
    private _usertype: string

    public constructor(username: string, usertype: string) {
        this._username = username
        this._usertype = usertype
    }

    get username(): string {
        return this._username
    }

    set username(username: string) {
        this._username = username
    }

    get usertype(): string {
        return this._usertype
    }

    set usertype(usertype: string) {
        this._usertype = usertype
    }
}
