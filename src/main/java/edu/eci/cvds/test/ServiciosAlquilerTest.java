package edu.eci.cvds.test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import org.apache.ibatis.cache.NullCacheKey;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ServiciosAlquilerTest {

    @Inject
    private final ServiciosAlquiler serviciosAlquiler;

    public ServiciosAlquilerTest() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquiler();
    }


    @Test
    public void emptyDB() {
        for(int i = 0; i < 100; i += 10) {
            boolean r = false;
            try {
                Cliente cliente =  serviciosAlquiler.consultarCliente(71);
                cliente.getVetado();
            } catch(ExcepcionServiciosAlquiler | NullPointerException | IndexOutOfBoundsException e) {
                r = true;
            }
            // Validate no Client was found;
            Assert.assertTrue(r);
        };
    }

    @Test
    public void verificarClienteVetado(){
        try{
            serviciosAlquiler.vetarCliente(1019139154,true);
            Cliente cliente = serviciosAlquiler.consultarCliente(1019139154);
            Assert.assertTrue(cliente.getVetado());
        }catch (ExcepcionServiciosAlquiler e){
            Assert.fail();
        }
    }

    @Test
    public void verificarInsercionDeCliente(){
        try{
            //serviciosAlquiler.registrarCliente(new Cliente("Camila",1234567899,"123456","Calle 456","cami@gmail.com"));
            serviciosAlquiler.consultarCliente(1234567899);
            Assert.assertTrue(true);
        }catch (ExcepcionServiciosAlquiler e){
            Assert.fail();
        }
    }

    @Test
    public void verificarErrorInsercionDeCliente(){
        try{
            serviciosAlquiler.registrarCliente(new Cliente("Camila",1234567899,"123456","Calle 456","cami@gmail.com"));
            Assert.fail();
        }catch (ExcepcionServiciosAlquiler e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void verificarConsultaDeClientes(){
        try {
            serviciosAlquiler.consultarClientes();
            Assert.assertTrue(true);
        }catch (ExcepcionServiciosAlquiler e){
            Assert.fail();
        }
    }

    @Test
    public void verificarConsultaDeCLiente(){
        try{
            serviciosAlquiler.consultarCliente(1);
            Assert.assertTrue(true);
        }catch (ExcepcionServiciosAlquiler e){
            Assert.fail();
        }
    }

    @Test
    public void verificarErrorCLienteInexistente(){
        try {
            Cliente cliente = serviciosAlquiler.consultarCliente(36);
            cliente.getNombre();
            Assert.fail();
        }catch (ExcepcionServiciosAlquiler | NullPointerException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void verificarCostoDeAlquiler(){
        try{
            Assert.assertEquals(serviciosAlquiler.consultarCostoAlquiler(1,2),32);
        }catch (ExcepcionServiciosAlquiler e){
            Assert.fail();
        }
    }

    @Test
    public void verificarActualizacionDeTarifaItem(){
        try{
            serviciosAlquiler.actualizarTarifaItem(2,5000);
            Item item = serviciosAlquiler.consultarItem(2);
            Assert.assertEquals(item.getTarifaxDia(),5000);
        }catch (ExcepcionServiciosAlquiler e){
            Assert.fail();
        }
    }

    @Test
    public void verificarInsercionItem(){
        try{
            /*serviciosAlquiler.registrarItem(new Item(new TipoItem(2,"Accion"),19,"Jugo",
                    "Naranja", Date.valueOf(LocalDate.now()),2000,"Hola","Bebida"));*/
            Item item = serviciosAlquiler.consultarItem(19);
            item.getId();
            Assert.assertTrue(true);
        } catch (ExcepcionServiciosAlquiler | NullPointerException excepcionServiciosAlquiler) {
            Assert.fail();
        }
    }

    @Test
    public void verificarConsultaItem(){
        try{
            serviciosAlquiler.consultarItem(19);
            Assert.assertTrue(true);
        }catch (ExcepcionServiciosAlquiler e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void verificarErrorConsultaItemInexistente(){
        try{
            Item item = serviciosAlquiler.consultarItem(20);
            item.getId();
            Assert.fail();
        }catch (ExcepcionServiciosAlquiler  | NullPointerException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void verificarConsultaItems() throws ExcepcionServiciosAlquiler {
        try{
            List<Item> items = serviciosAlquiler.consultarItemsDisponibles();
            items.get(0).getId();
            Assert.assertTrue(true);
        }catch (ExcepcionServiciosAlquiler | NullPointerException e){
            Assert.fail();
        }
    }

    @Test
    public void verificarConsultaTipoItems() throws ExcepcionServiciosAlquiler{
        try{
            List<TipoItem> tipoItems = serviciosAlquiler.consultarTiposItem();
            tipoItems.get(0).getID();
            Assert.assertTrue(true);
        }catch (ExcepcionServiciosAlquiler | NullPointerException e){
            Assert.fail();
        }
    }

    @Test
    public void verificarConsultaTipoItem() throws ExcepcionServiciosAlquiler{
        try{
            TipoItem tipoItem = serviciosAlquiler.consultarTipoItem(1);
            tipoItem.getID();
            Assert.assertTrue(true);
        }catch (ExcepcionServiciosAlquiler | NullPointerException e){
            Assert.fail();
        }
    }

    @Test
    public void verificarRegistroDeAlquiler() throws ExcepcionServiciosAlquiler{
        try{
            Item item = serviciosAlquiler.consultarItem(1);
            serviciosAlquiler.registrarAlquilerCliente(Date.valueOf(LocalDate.now()),1019139154,item,5);
            Assert.assertTrue(true);
        }catch (ExcepcionServiciosAlquiler e){
            Assert.fail();
        }
    }

    @Test
    public void verificarMulta() throws ExcepcionServiciosAlquiler{
        try{
            Assert.assertEquals(serviciosAlquiler.valorMultaRetrasoxDia(2),5000);
        }catch (ExcepcionServiciosAlquiler e){
            Assert.fail();
        }
    }
    

}