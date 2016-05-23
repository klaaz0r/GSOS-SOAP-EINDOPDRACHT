package nl.hu.fnt.gsos.wsproducer;

import java.math.BigInteger;

import javax.jws.WebService;

import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;

import nl.hu.fnt.gsos.wsinterface.CreateIban;
import nl.hu.fnt.gsos.wsinterface.IbanFault_Exception;
import nl.hu.fnt.gsos.wsinterface.WSInterface;

@WebService(endpointInterface = "nl.hu.fnt.gsos.wsinterface.WSInterface")
public class IbanServiceImpl implements WSInterface {

	@Override
	public String generateIban(CreateIban parameters) throws IbanFault_Exception {
		Iban iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode(parameters.getBankcode())
				.accountNumber("" + parameters.getRekeningsnummer()).build();
		return iban.toFormattedString();
	}

	@Override
	public BigInteger validateIban(String parameters) throws IbanFault_Exception {
		// How to validate Iban
		try {
			IbanUtil.validate(parameters);
			return BigInteger.ONE;
		} catch (IbanFormatException | InvalidCheckDigitException | UnsupportedCountryException e) {
			return BigInteger.ZERO;
		}
	}

	/*
	 * @Override public PowerResponse calculatePower(PowerRequest request)
	 * throws PowerFault_Exception { ObjectFactory factory = new
	 * ObjectFactory(); PowerResponse response = factory.createPowerResponse();
	 * try { Double result = Math.pow(request.getX().doubleValue(), request
	 * .getPower().doubleValue()); // x en power zijn altijd gehele getallen dan
	 * is er geen afronding long actualResult = result.longValue();
	 * response.setResult(BigInteger.valueOf(actualResult)); } catch
	 * (RuntimeException e) { PowerFault x = factory.createPowerFault();
	 * x.setErrorCode((short) 1); x.setMessage("Kan de macht van " +
	 * request.getX() + " tot de macht " + request.getPower().toString() +
	 * " niet berekenen."); PowerFault_Exception fault = new
	 * PowerFault_Exception( "Er ging iets mis met het berekenen van de power",
	 * x); throw fault; } return response; }
	 */
}