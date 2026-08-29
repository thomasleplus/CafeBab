# CafeBab

Arduino project to detect usage of a table football.

[![Maven](https://github.com/thomasleplus/CafeBab/workflows/Maven/badge.svg)](https://github.com/thomasleplus/CafeBab/actions?query=workflow:"Maven")

## Structure

The system: an XBee-equipped Arduino on the foosball table detects activity and
sends events over the air; a host application receives them and publishes usage.

- [`arduino/`](arduino/) — Arduino sketches for the on-table sensor device.
- [`babduino/`](babduino/) — the Java host application that reads the XBee feed
  and publishes measurements.
- [`xbee/`](xbee/) — Digi X-CTU radio configuration profiles for the XBee modules.
- [`utils/`](utils/) — third-party drivers/tools for the XBee/USB hardware.

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## Security

Please read [SECURITY.md](SECURITY.md) for details on our security policy and how to report security vulnerabilities.

## Code of Conduct

Please read [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) for details on our code of conduct.

## License

This project is licensed under the terms of the [LICENSE](LICENSE) file.
