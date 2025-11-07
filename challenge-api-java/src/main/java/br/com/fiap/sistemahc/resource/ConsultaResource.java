package br.com.fiap.sistemahc.resource;
import br.com.fiap.sistemahc.model.Consulta;
import br.com.fiap.sistemahc.service.ConsultaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaResource {
    @Inject
    ConsultaService service;

    @GET
    public List<Consulta> listar() { return service.listar(); }

    @GET
    @Path("/{id}")
    public Consulta buscar(@PathParam("id") String id) { return service.buscar(id); }

    @GET
    @Path("/usuario/{usuarioId}")
    public List<Consulta> porUsuario(@PathParam("usuarioId") String usuarioId) { return service.findByUsuarioId(usuarioId); }

    @POST
    public Response criar(@Valid Consulta c) {
        Consulta criado = service.criar(c);
        return Response.status(Response.Status.CREATED).entity(criado).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") String id, @Valid Consulta c) {
        Consulta atualizado = service.atualizar(id, c);
        return Response.ok(atualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") String id) {
        service.deletar(id);
        return Response.noContent().build();
    }
}