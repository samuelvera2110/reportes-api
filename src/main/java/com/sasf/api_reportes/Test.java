/*package com.bolivariano.microservice.intmsoregistrocivil.services;

import com.bolivariano.microservice.intmsoregistrocivil.core.configuration.FrameworkParamsLoader;
import com.bolivariano.microservice.intmsoregistrocivil.core.constants.Constants;
import com.bolivariano.microservice.intmsoregistrocivil.core.exception.RegistroCivilException;
import com.bolivariano.microservice.intmsoregistrocivil.core.exception.SPCalledExeption;
import com.bolivariano.microservice.intmsoregistrocivil.core.ws.RegistroCivilClient;
import com.bolivariano.microservice.intmsoregistrocivil.dao.RegistroCivilDao;
import com.bolivariano.microservice.intmsoregistrocivil.dao.spmodel.HomologarMensajeSp;
import com.bolivariano.microservice.intmsoregistrocivil.dao.spmodel.HomologarRC;
import com.bolivariano.microservice.intmsoregistrocivil.dao.spmodel.RegistrarInfoCliente;
import com.bolivariano.microservice.intmsoregistrocivil.dto.MensajeEntradaConsultar;
import com.bolivariano.microservice.intmsoregistrocivil.dto.MensajeSalidaConsultar;
import com.bolivariano.microservice.intmsoregistrocivil.dto.RegistroCivil;
import com.bolivariano.microservice.intmsoregistrocivil.wsl.Ciudadano;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegistroCivilService implements IRegistroCivilService{

    private RegistroCivilClient registroCivilClient;
    private final FrameworkParamsLoader frameworkParamsLoader;
    private final RegistroCivilDao registroCivilDao;

    @Autowired
    public RegistroCivilService(FrameworkParamsLoader frameworkParamsLoader, RegistroCivilClient registroCivilClient, RegistroCivilDao registroCivilDao) {
        this.frameworkParamsLoader = frameworkParamsLoader;
        this.registroCivilClient = registroCivilClient;
        this.registroCivilDao = registroCivilDao;
    }

    public MensajeSalidaConsultar consultar(MensajeEntradaConsultar msgEntradaConsultar) throws RegistroCivilException, SPCalledExeption {
        MensajeSalidaConsultar msgSalidaConsultar = new MensajeSalidaConsultar();
        Ciudadano ciudadano;
        try {
            // Consulta Web service
            String codInstitucion = frameworkParamsLoader.getCodigoInstitucion();
            String codAgencia = frameworkParamsLoader.getCodigoAgencia();
            String usuario = frameworkParamsLoader.getUsuario();
            String contrasenia = frameworkParamsLoader.getClave();
            String nui = msgEntradaConsultar.getIdentificacion();

            ciudadano = registroCivilClient.busquedaPorNui(codInstitucion, codAgencia, usuario, contrasenia, nui);

            if (!"000".equalsIgnoreCase(ciudadano.getCodigoError())) {
                var mensajeHomologado = homologarMensaje(
                        Integer.parseInt(ciudadano.getCodigoError()),
                        "cl_cat_msj_web_rcivil",
                        ciudadano.getError()
                );
                msgSalidaConsultar.setCodigoError(0);
                msgSalidaConsultar.setMensajeSistema(ciudadano.getCodigoError() + " - " + ciudadano.getError());
                msgSalidaConsultar.setMensajeUsuario(mensajeHomologado);

                return msgSalidaConsultar;
            }
        }  catch (Exception e) {
            RegistroCivilException regCivilEx = new RegistroCivilException();
            regCivilEx.setCatalogoError("sv_errores_ts");
            regCivilEx.setCodigoError("70013");
            regCivilEx.setMensajeError("REGISTRO CIVIL NO DISPONIBLE");
            throw regCivilEx;
        }

        // Homologacion RC
        HomologarRC sp = new HomologarRC();
        sp.setEUsuario(msgEntradaConsultar.getUsuario());
        sp.setECedula(msgEntradaConsultar.getIdentificacion());
        sp.setETipNacionalidad(ciudadano.getNacionalidad());
        sp.setESexo(ciudadano.getSexo());
        sp.setEEstadoCivil(ciudadano.getEstadoCivil());
        sp.setEProfesion(ciudadano.getProfesion());

        var response = registroCivilDao.homologarRC(sp);
        if (response == null || !response.containsKey(Constants.RETURN_VALUE)) {
            throw new SPCalledExeption(Constants.SP_HOMOLOGA_RC);
        }
        var returnValue = (Integer) response.get(Constants.RETURN_VALUE);

        msgSalidaConsultar.setCodigoError(0);
        msgSalidaConsultar.setMensajeSistema(ciudadano.getCodigoError() + " - " + ciudadano.getError());
        msgSalidaConsultar.setMensajeUsuario(ciudadano.getError());
        // ----------------- Armar <RegistroCivil> -----------------
        RegistroCivil registroCivil = new RegistroCivil();
        registroCivil.setNombre(translate(ciudadano.getNombre()));
        registroCivil.setNombres(translate(ciudadano.getNombres()));
        registroCivil.setApellidos(translate(ciudadano.getApellidos()));
        registroCivil.setCondicionCedulado(ciudadano.getCondicionCedulado());
        registroCivil.setFechaNacimiento(ciudadano.getFechaNacimiento());
        registroCivil.setNacionalidad(ciudadano.getNacionalidad());
        registroCivil.setEstadoCivil(ciudadano.getEstadoCivil());
        registroCivil.setConyuge(translate(ciudadano.getConyuge()));
        registroCivil.setInstruccion(ciudadano.getInstruccion());
        registroCivil.setProfesion(ciudadano.getProfesion());
        registroCivil.setFechaFallecimiento(ciudadano.getFechaFallecimiento());
        registroCivil.setFechaCedulacion(ciudadano.getFechaCedulacion());
        registroCivil.setDomicilio(ciudadano.getDomicilio());
        registroCivil.setCalle(ciudadano.getCalle());
        registroCivil.setNumeroCasa(ciudadano.getNumeroCasa());
        registroCivil.setSexo(ciudadano.getSexo());
        registroCivil.setNombrePadre(translate(ciudadano.getNombrePadre()));
        registroCivil.setNombreMadre(translate(ciudadano.getNombreMadre()));
        registroCivil.setLugarNacimiento(ciudadano.getLugarNacimiento());
        registroCivil.setFechaMatrimonio(ciudadano.getFechaMatrimonio());
        registroCivil.setHomMISNacionalidad((String) response.get("s_cod_nacionalidad"));
        registroCivil.setHomMISSexo((String) response.get("s_cod_sexo"));
        registroCivil.setHomMISEstCivil((String) response.get("s_cod_est_civil"));
        registroCivil.setHomMISProfesion((String) response.get("s_cod_profesion"));
        msgSalidaConsultar.setRegistroCivil(registroCivil);

        // Control uso web service
        if (    "000".equalsIgnoreCase(ciudadano.getCodigoError()) ||
                "009".equalsIgnoreCase(ciudadano.getCodigoError()) ||
                "005".equalsIgnoreCase(ciudadano.getCodigoError())) {
            RegistrarInfoCliente spRegInfClient = new RegistrarInfoCliente();
            spRegInfClient.setEIdentificacion(msgEntradaConsultar.getIdentificacion());
            spRegInfClient.setEUsuario(msgEntradaConsultar.getUsuario());
            spRegInfClient.setECanal(msgEntradaConsultar.getCanal());
            var responseRegInfClient = registroCivilDao.registrarInfoCliente(spRegInfClient);
            if (responseRegInfClient == null || !responseRegInfClient.containsKey(Constants.RETURN_VALUE)) {
                throw new SPCalledExeption(Constants.SP_IREG_INF_ID_CLIENTE);
            }
        }

        return msgSalidaConsultar;
    }

    @Override
    public String homologarMensaje(int codigoError, String sistema, String mensaje) {
        var sp = new HomologarMensajeSp();
        sp.setNumero(codigoError); //codigo
        sp.setSrv(sistema); //sistema
        sp.setIMsg(mensaje); //mensaje
        Map<String, Object> response = registroCivilDao.homologarMensaje(sp);
        if (response != null && response.containsKey(Constants.SP_MSG_PARAM)) {
            return response.get(Constants.SP_MSG_PARAM).toString();
        }
        return null;
    }

    // Equivalente a fn:translate( , "ÁÉÍÓÚ", "AEIOU")
    private String translate(String value) {
        if (value == null) {
            return null;
        }
        return value
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U");
    }

}*/