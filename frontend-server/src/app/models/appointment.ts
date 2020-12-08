export class Appointment {
    private _patient: string;
    private _doctor: string;
    private _date: number;

    constructor(doctor: string, date: number) {
        this._doctor = doctor;
        this._date = date;
    }

    get patient() {
        return this._patient;
    }

    set patient(patient: string) {
        this._patient = patient;
    }

    get doctor() {
        return this._doctor;
    }

    set doctor(doctor: string) {
        this._doctor = doctor;
    }

    get date() {
        return this._date;
    }

    set date(date: number) {
        this._date = date;
    }
}
