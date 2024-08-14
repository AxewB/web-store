
class ResponseHandler {
  static success(response, message) {
    return {
      res: {
        response,
        data: response.data,
        message
      },
      err: null
    }
  }
  static error(error, message) {
    return {
      res: null,
      err: {
        error,
        message
      }
    }
  }
}

export default ResponseHandler;

