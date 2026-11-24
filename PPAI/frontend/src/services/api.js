import axios from 'axios';

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json',
  },
});

function extractErrorMessage(error, fallbackMessage) {
  if (error.response) {
    const data = error.response.data;
    if (data && typeof data === 'object') {
      return data.error || data.mensaje || data.message || fallbackMessage;
    }
    if (typeof data === 'string' && data.trim()) {
      return data;
    }
    return `Error HTTP ${error.response.status}`;
  }
  if (error.request) {
    return 'No se pudo conectar con el servidor backend (http://localhost:8081). Verifique que el servicio esté en ejecución.';
  }
  return error.message || fallbackMessage;
}

export async function login(nombre, contrasena) {
  try {
    await apiClient.post('/auth/login', { nombre, contrasena });
    return { ok: true };
  } catch (error) {
    throw new Error(extractErrorMessage(error, 'Error al iniciar sesión.'));
  }
}

export async function logout() {
  try {
    const response = await apiClient.post('/auth/logout');
    return response.data;
  } catch (error) {
    throw new Error(extractErrorMessage(error, 'Error al cerrar sesión.'));
  }
}

export async function consultarUbicacionBolsines() {
  try {
    const response = await apiClient.get('/bolsines/consultar-ubicacion');
    return { ok: true, data: response.data };
  } catch (error) {
    if (error.response?.status === 404 && error.response?.data) {
      return {
        ok: false,
        notFound: true,
        data: error.response.data,
      };
    }
    if (error.response?.status === 500) {
      throw new Error(
        'No hay una sesión activa de Encargado de Bolsines o ocurrió un error en el servidor. Intente iniciar sesión como encargado1.'
      );
    }
    throw new Error(extractErrorMessage(error, 'Error al consultar la ubicación de los bolsines.'));
  }
}

export async function filtrarBolsines({ numeroPrecinto, codigoCmDestino }) {
  const params = {};
  if (numeroPrecinto !== '' && numeroPrecinto !== null && numeroPrecinto !== undefined) {
    const precinto = Number.parseInt(numeroPrecinto, 10);
    if (!Number.isNaN(precinto)) {
      params.numeroPrecinto = precinto;
    }
  }
  if (codigoCmDestino && codigoCmDestino.trim() !== '') {
    params.codigoCmDestino = codigoCmDestino.trim();
  }

  try {
    const response = await apiClient.get('/bolsines/filtrar', { params });
    return { ok: true, data: response.data };
  } catch (error) {
    if (error.response?.status === 404 && error.response?.data) {
      return {
        ok: false,
        notFound: true,
        data: error.response.data,
      };
    }
    if (error.response?.status === 500) {
      throw new Error(
        'No hay una sesión activa o ocurrió un error al filtrar. Intente iniciar sesión como encargado1.'
      );
    }
    throw new Error(extractErrorMessage(error, 'Error al filtrar los bolsines.'));
  }
}

export async function seleccionarBolsin(numeroBolsin) {
  try {
    const response = await apiClient.post('/bolsines/seleccionar-bolsin', null, {
      params: { numeroBolsin: Number(numeroBolsin) },
    });
    return response.data;
  } catch (error) {
    throw new Error(extractErrorMessage(error, 'No se pudo seleccionar el bolsín indicado.'));
  }
}

export async function confirmarEnvioCorreo(confirmado) {
  try {
    const response = await apiClient.post('/bolsines/confirmar-envio-correo', null, {
      params: { confirmado: Boolean(confirmado) },
    });
    return response.data;
  } catch (error) {
    throw new Error(extractErrorMessage(error, 'Error al confirmar el envío de correo electrónico.'));
  }
}

export default apiClient;
