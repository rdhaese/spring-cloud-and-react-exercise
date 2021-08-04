const Server = {
    Url: 'http://localhost',
    Port: '8762'
};

const RootUrl = Server.Url + ':' + Server.Port;

const Services = {
    AddressService: RootUrl + '/address-service/address' ,
    StudentService: RootUrl + '/student-service/student',
};

export default Services;